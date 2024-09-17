package com.stock.services;

public interface IUserService<T, L> {
    T get(L id);

    T getByUsername(String username);

    String getUserRolesByUsername(String username);

    void logged(String username, String token);
}
