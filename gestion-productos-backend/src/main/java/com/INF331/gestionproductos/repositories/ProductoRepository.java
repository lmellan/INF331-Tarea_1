package com.INF331.gestionproductos.repositories;

import com.INF331.gestionproductos.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
