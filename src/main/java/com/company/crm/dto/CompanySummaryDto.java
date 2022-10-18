package com.company.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanySummaryDto implements RowMapper<CompanySummaryDto> {
    private String companyName;
    private Integer conversationCount;
    private String mostPopularUser;

    /**
     * Implementations must implement this method to map each row of data
     * in the ResultSet. This method should not call {@code next()} on
     * the ResultSet; it is only supposed to map values of the current row.
     *
     * @param rs     the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if an SQLException is encountered getting
     *                      column values (that is, there's no need to catch SQLException)
     */
    @Override
    public CompanySummaryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompanySummaryDto dto = new CompanySummaryDto();
        dto.companyName = rs.getString(1);
        dto.conversationCount = rs.getInt(2);
        dto.mostPopularUser = rs.getString(3);
        return dto;
    }
}
