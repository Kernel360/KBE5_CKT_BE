package kernel360.ckt.admin.application.service.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerKeywordCommand {
    private final Long companyId;
    private final String keyword;
}
