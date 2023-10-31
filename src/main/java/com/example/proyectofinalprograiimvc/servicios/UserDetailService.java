package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.config.UserDetailsImpl;
import com.example.proyectofinalprograiimvc.modelo.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final UsuarioServiceImpl usuarioService;

    public UserDetailService(UsuarioServiceImpl usuarioService){
        this.usuarioService = usuarioService;
    }
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new UserDetailsImpl(usuario);
    }
}
