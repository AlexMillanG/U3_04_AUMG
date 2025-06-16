package mx.edu.utez.AplicacionDePrincipios.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiResponse {

    public Object data;
    public String message;
    public boolean success;

    public ApiResponse(Object data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }
}
