package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
