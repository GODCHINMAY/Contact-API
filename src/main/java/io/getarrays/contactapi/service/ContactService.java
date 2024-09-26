package io.getarrays.contactapi.service;

import io.getarrays.contactapi.domain.Contact;
import io.getarrays.contactapi.repo.ContactRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiFunction;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepo contactRepo;

    public Page<Contact> getAllContacts(int page, int size){
        return contactRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Contact getContact(String id){
        return contactRepo.findById(id).orElseThrow(()-> new RuntimeException("Contact not found"));
    }

    public Contact createContact(Contact contact){
        return contactRepo.save(contact);
    }

    public void deleteContact(Contact contact){
         contactRepo.delete(contact);
    }

    public String uploadContact(String id, MultipartFile file){
        Contact contact = getContact(id);
        String photoUrl = null;
        contact.setPhotourl(photoUrl);
        contactRepo.save(contact);
        return photoUrl;
    }

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        try{
            Path fileStorageLocation = Paths.get("").toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)){
                Files.createDirectories(fileStorageLocation);
            }

        }catch(Exception exception){
            throw new RuntimeException("Unable to save image");
        }
    };
}

















