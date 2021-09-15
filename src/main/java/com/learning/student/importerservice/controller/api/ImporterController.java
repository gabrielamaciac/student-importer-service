package com.learning.student.importerservice.controller.api;

import com.learning.student.importerservice.controller.model.StudentDto;
import com.learning.student.importerservice.facade.ImporterFacadeImpl;
import com.learning.student.importerservice.integration.model.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@Tag(name = "Importer Service", description = "The importer service API for importing a student from JSON.")
public class ImporterController implements ImporterApi {

    public final ImporterFacadeImpl importerFacade;
    private final ModelMapper modelMapper = new ModelMapper();

    public ImporterController(ImporterFacadeImpl importerFacade) {
        this.importerFacade = importerFacade;
    }

    @Override
    @Operation(summary = "Import a student.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student imported.", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = StudentDto.class))})})
    @PostMapping("/import")
    public ResponseEntity<String> sendStudent(@Parameter(description = "student object to be imported") @RequestBody StudentDto studentDto) {
        importerFacade.importStudent(modelMapper.map(studentDto, Student.class));
        return new ResponseEntity<>("Student JSON sent to queue.", HttpStatus.OK);
    }
}