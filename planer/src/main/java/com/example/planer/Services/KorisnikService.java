package com.example.planer.Services;


import com.example.planer.Models.Korisnik;
import com.example.planer.Models.Role;
import com.example.planer.Repositories.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class KorisnikService {

    @Autowired
    KorisnikRepository korisnikRepository;

    public Korisnik registerKorisnik(Korisnik korisnik){

        if(korisnikRepository.existsByEmail(korisnik.getEmail())){
            throw new RuntimeException("Korisnik sa istom email adresom vec postoji");
        }

        if(!korisnik.podudaranjeLozinki()){
            throw new RuntimeException("Lozinke se ne podudaraju");
        }else{

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            korisnik.setLozinka(encoder.encode(korisnik.getLozinka()));

            Set<Role> roles = korisnik.getRoles();
            if (roles == null || roles.isEmpty()) {
                roles = new HashSet<>();
                roles.add(Role.KORISNIK);
                korisnik.setRoles(roles);
            }

        }

        return korisnikRepository.save(korisnik);
    }


    public void loginKorisnik(String email, String lozinka) {

        Korisnik korisnik = korisnikRepository.findByEmail(email);

        if (korisnik == null) {
            throw new RuntimeException("Neuspješna prijava. Provjerite e-mail adresu i lozinku.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(lozinka, korisnik.getLozinka())) {
            throw new RuntimeException("Neuspješna prijava. Provjerite e-mail adresu i lozinku.");
        }
    }


    public void admin(){

        Korisnik admin = new Korisnik();
        admin.setIme("admin");
        admin.setPrezime("admin");
        admin.setEmail("admin@admin.com");
        admin.setLozinka("admin123");

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        admin.setRoles(roles);
        Korisnik existingAdmin = korisnikRepository.findByEmail("admin@admin.com");

        if(existingAdmin == null){
            this.registerKorisnik(admin);
        }else {
            System.out.println("Admin već postoji.");
        }

    }


    //admin
    public List<Korisnik> getAllKorisnici(){
        return korisnikRepository.findAll();
    }
    //korisnik by id
    public Korisnik getKorisnikById(Long id){
        return korisnikRepository.findById(id).orElse(null);
    }
    //admin
    public Korisnik createKorisnik(Korisnik korisnik){
        return korisnikRepository.save(korisnik);
    }
    //admin
    public void deleteKorisnik(Long id){
        korisnikRepository.deleteById(id);
    }
    //admin
    public Korisnik updateKorisnik(Long id, Korisnik korisnik) {
        Optional<Korisnik> existingKorisnik = korisnikRepository.findById(id);
        if (existingKorisnik.isPresent()) {
            Korisnik oldKorisnik = existingKorisnik.get();
            oldKorisnik.setIme(korisnik.getIme());
            oldKorisnik.setPrezime(korisnik.getPrezime());
            oldKorisnik.setLozinka(korisnik.getLozinka());
            oldKorisnik.setPotvrdaLozinke(korisnik.getPotvrdaLozinke());
            oldKorisnik.setEmail(korisnik.getEmail());
            return korisnikRepository.save(oldKorisnik);
        } else {
            throw new EntityNotFoundException("Korisnik s ID-om " + id + " nije pronađen.");
        }
    }


}
