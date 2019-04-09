package com.student.rating.utility;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.student.rating.repository.GroupRepository;

import static com.student.rating.utility.StaticDataEngine.GROUP_LIST;


@EnableScheduling
public class CronUtility {
    private static final Logger LOG = LoggerFactory.getLogger(CronUtility.class);

    private final GroupRepository groupRepository;

    @Autowired
    public CronUtility(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Scheduled(cron = "0 0 0 28 * ?")
    public void refreshData() {
        LOG.info("Starting refreshing groups at {}. Group size is {}", DateTime.now().toDate(),GROUP_LIST.size());
        GROUP_LIST = groupRepository.findAll();
        LOG.info("Groups was successfully fetched. Group size is {}",GROUP_LIST.size());
    }
}
