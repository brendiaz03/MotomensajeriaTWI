package com.tallerwebi.dominio.compania;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCompaniaImpl implements IServiceCompania {

        private IRepositorioCompania repositorioCompania;

        @Autowired
        public ServiceCompaniaImpl(IRepositorioCompania repositorioCompania) {
            this.repositorioCompania = repositorioCompania;
        }

    }
