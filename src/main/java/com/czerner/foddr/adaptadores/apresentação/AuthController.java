package com.czerner.foddr.adaptadores.apresentação;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.czerner.foddr.adaptadores.apresentação.Requests.RegisterUserRequest;
import com.czerner.foddr.aplicação.ActivateUserUC;
import com.czerner.foddr.aplicação.RegisterUserUC;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUC registerUserUC;
    private final ActivateUserUC activateUserUC;
    

    public AuthController(RegisterUserUC registerUserUC, ActivateUserUC activateUserUC) {
        this.registerUserUC = registerUserUC;
        this.activateUserUC = activateUserUC;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest dto) {
        registerUserUC.execute(dto);
        return ResponseEntity.ok("Conta criada");
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam String token) {
        activateUserUC.execute(token);
        return ResponseEntity.ok("Conta ativada com sucesso");
    }
}
