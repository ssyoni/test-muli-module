package modules.be.domain.service;

import lombok.RequiredArgsConstructor;
import modules.be.domain.dto.SearchWordResponse;
import modules.be.domain.entity.SearchWordLog;
import modules.be.domain.repository.SearchWordLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchWordLogService {
    private final SearchWordLogRepository searchWordLogRepository;

    @Transactional
    public SearchWordResponse writeSearchWord(String keyword){
        SearchWordLog searchWordLog = searchWordLogRepository.findByKeyword(keyword);
        System.out.println("searchWordLog :: " + searchWordLog);
        boolean isWordExist = searchWordLog != null;
        String action = "";
        // update
        if (isWordExist){
            searchWordLog.updateScore();
            action = "update";
        }
        // insert
        else{
            searchWordLog = searchWordLogRepository.save(new SearchWordLog(keyword));
            action = "insert";
        }

        return SearchWordResponse.builder()
                .entity(searchWordLog)
                .action(action)
                .build();
    }
}
