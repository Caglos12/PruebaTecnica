package com.example.pruebatecnica.ui.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UiState<T> {

    public enum Status { LOADING, SUCCESS, ERROR }

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    private UiState(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> UiState<T> loading() {
        return new UiState<>(Status.LOADING, null, null);
    }

    public static <T> UiState<T> success(@NonNull T data) {
        return new UiState<>(Status.SUCCESS, data, null);
    }

    public static <T> UiState<T> error(@NonNull String message) {
        return new UiState<>(Status.ERROR, null, message);
    }
}
