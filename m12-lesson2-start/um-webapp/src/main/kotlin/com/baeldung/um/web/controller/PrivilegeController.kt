package com.baeldung.um.web.controller;

import com.baeldung.common.web.controller.AbstractController
import com.baeldung.um.persistence.model.Privilege
import com.baeldung.um.service.IPrivilegeService;
import com.baeldung.um.util.UmMappings;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UmMappings.PRIVILEGES)
 class PrivilegeController(private val service: IPrivilegeService): AbstractController<Privilege>(){

    @GetMapping("/{id}")
      fun findOne(@PathVariable("id")  id : Long ): Privilege = findOneInternal(id) ;
    // Spring
    override fun getService() = service ;
}
