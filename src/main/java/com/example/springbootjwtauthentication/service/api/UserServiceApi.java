package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Role;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import com.example.springbootjwtauthentication.payload.request.UpdateFirebaseTokenRequest;
import com.example.springbootjwtauthentication.payload.request.UserInfoRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.ProductResponse;
import com.example.springbootjwtauthentication.payload.response.StatusResponse;
import com.example.springbootjwtauthentication.payload.response.UserInfoResponse;
import com.example.springbootjwtauthentication.security.service.RefreshTokenService;
import com.example.springbootjwtauthentication.service.implementations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceApi {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public ResponseEntity<UserInfoResponse> getUserAccount(Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user.toUserInfoResponse());
    }

    public ResponseEntity<MessageResponse> updateUserAccount(Long userId, UserInfoRequest request) {
        User user = userService.getUserById(userId);
        user.update(request);
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Usuario actualizado con éxito"));
    }

    public ResponseEntity<MessageResponse> deleteUserAccount(Long userId) {

        refreshTokenService.deleteByUserId(userId);
        userService.deleteUser(userId);

        return ResponseEntity.ok(new MessageResponse("Usuario eliminado con éxito"));
    }


    public ResponseEntity<MessageResponse> updateFirebaseToken(Long userId, UpdateFirebaseTokenRequest request) {
        User user = userService.getUserById(userId);
        user.updateFirebaseToken(request);
        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("Token de firebase actualizado correctamente"));
    }


    public ResponseEntity<List<OrderInfoResponse>> getAllOrderInfo(Long userId) {
        User user = userService.getUserById(userId);

        Set<Role> roles = user.getRoleEntities();

        Role producer = roleService.getRoleByName(ERole.ROLE_PRODUCER);
        Role customer = roleService.getRoleByName(ERole.ROLE_CUSTOMER);
        Role transporter = roleService.getRoleByName(ERole.ROLE_TRANSPORTER);

        List<Order> orderList = null;
        if (roles.contains(producer)) {
            orderList = orderService.getAllOrdersByProducerId(userId);
        }

        if (roles.contains(customer)) {
            orderList = orderService.getAllOrdersByCustomerId(userId);
        }

        if (roles.contains(transporter)) {
            orderList = orderService.getAllOrdersByTransporterId(userId);
        }
        if (orderList != null) {
            List<OrderInfoResponse> orderInfoResponseList = getOrderInfoResponseList(orderList);
            return ResponseEntity.ok().body(orderInfoResponseList);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private List<OrderInfoResponse> getOrderInfoResponseList(List<Order> orderList) {
        List<OrderInfoResponse> orderInfoResponseList = new ArrayList<>();

        orderList.forEach(
                order -> {
                    List<StatusResponse> statusResponseList = getStatusResponseList(order);

                    List<ProductResponse> productResponseList = getProductResponseList(order);

                    orderInfoResponseList.add(order.toOrderInfoResponse(statusResponseList, productResponseList));
                }
        );
        return orderInfoResponseList;
    }

    private List<StatusResponse> getStatusResponseList(Order order) {
        List<StatusResponse> statusResponseList = new ArrayList<>();
        orderStatusService.getAllByOrderId(order.getId()).forEach(orderStatus -> {
                    statusResponseList.add(
                            orderStatus.toStatusResponse()
                    );
                }
        );
        return statusResponseList;
    }

    private List<ProductResponse> getProductResponseList(Order order) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        orderProductService.getAllOrderProductsByOrderId(order.getId()).forEach(orderProduct -> {
            productResponseList.add(
                    orderProduct.toProductResponse()
            );
        });
        return productResponseList;
    }
}
