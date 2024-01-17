package com.findar.demo.copy_status;

import com.findar.demo.entity.CopyStatus;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TreeMap;

@Service
@Scope("singleton")
@AllArgsConstructor
public class CopyStatusService
{
    private final CopyStatusRepository copyStatusRepository;

    private final TreeMap<Integer, CopyStatus> status;

    @PostConstruct
    public void loadAllCopyStatus()
    {

        for (CopyStatus cs : copyStatusRepository.findAll())
        {
            status.put(cs.getId(), cs);
        }

    }

    public CopyStatus getById(int id)
    {
        return status.get(id);
    }

    public CopyStatus getByStatus(String statusToFind)
    {
        return
                status.values()
                        .stream()
                        .filter(cs -> cs.getStatus().equals(statusToFind))
                        .findFirst()
                        .orElse(null);
    }

}
