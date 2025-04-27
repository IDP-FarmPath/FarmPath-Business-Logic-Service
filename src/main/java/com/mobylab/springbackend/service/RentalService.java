package com.mobylab.springbackend.service;



import com.mobylab.springbackend.entity.Equipment;
import com.mobylab.springbackend.entity.Rental;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.repository.EquipmentRepository;
import com.mobylab.springbackend.repository.RentalRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.RentalRequestDTO;
import com.mobylab.springbackend.service.dto.RentalResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
@Profile("backend")
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository,
                         EquipmentRepository equipmentRepository,
                         UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    public RentalResponseDTO create(RentalRequestDTO dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found"));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Rental rental = new Rental();
        rental.setEquipment(equipment);
        rental.setUser(user);
        rental.setStartDate(dto.getStartDate());
        rental.setEndDate(dto.getEndDate());

        Rental saved = rentalRepository.save(rental);

        RentalResponseDTO response = new RentalResponseDTO();
        response.setId(saved.getId());
        response.setStartDate(saved.getStartDate());
        response.setEndDate(saved.getEndDate());
        response.setEquipmentName(saved.getEquipment().getName());
        response.setUserEmail(saved.getUser().getEmail());

        return response;
    }
}
