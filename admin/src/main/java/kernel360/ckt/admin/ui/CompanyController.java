package kernel360.ckt.admin.ui;

import kernel360.ckt.admin.application.command.CreateCompanyCommand;
import kernel360.ckt.admin.application.command.UpdateCompanyCommand;
import kernel360.ckt.admin.ui.dto.request.CompanyCreateRequest;
import kernel360.ckt.admin.ui.dto.request.CompanyUpdateRequest;
import kernel360.ckt.admin.ui.dto.response.CompanyCreateResponse;
import kernel360.ckt.admin.application.CompanyService;
import kernel360.ckt.admin.ui.dto.response.CompanyListResponse;
import kernel360.ckt.admin.ui.dto.response.CompanyMeResponse;
import kernel360.ckt.admin.ui.dto.response.CompanyResponse;
import kernel360.ckt.core.common.response.CommonResponse;
import kernel360.ckt.core.domain.entity.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    CommonResponse<CompanyListResponse> selectCompanyList() {
        final List<CompanyEntity> companies = companyService.findAll();
        return CommonResponse.success(CompanyListResponse.from(companies));
    }

    @GetMapping("/{id}")
    CommonResponse<CompanyResponse> selectCompany(@PathVariable Long id) {
        final CompanyEntity companyEntity = companyService.findById(id);
        return CommonResponse.success(CompanyResponse.from(companyEntity));
    }

    @PostMapping
    CommonResponse<CompanyCreateResponse> createCompany(@RequestBody CompanyCreateRequest request) {
        final CreateCompanyCommand command = request.toCommand();
        final CompanyEntity companyEntity = companyService.create(command);
        return CommonResponse.success(CompanyCreateResponse.from(companyEntity));
    }

    @PutMapping("/{id}")
    CommonResponse<CompanyResponse> updateCompany(@PathVariable Long id, @RequestBody CompanyUpdateRequest request) {
        final UpdateCompanyCommand command = request.toCommand();
        final CompanyEntity companyEntity = companyService.update(id, command);
        return CommonResponse.success(CompanyResponse.from(companyEntity));
    }

    @DeleteMapping("/{id}")
    CommonResponse<Void> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return CommonResponse.success(null);
    }

    @GetMapping("/me")
    CommonResponse<CompanyMeResponse> getMyCompany() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("로그인된 회사 정보를 찾을 수 없습니다.");
        }

        Long companyId = Long.parseLong(authentication.getPrincipal().toString());

        CompanyEntity company = companyService.findMyCompany(companyId);
        return CommonResponse.success(new CompanyMeResponse(company));
    }

}
