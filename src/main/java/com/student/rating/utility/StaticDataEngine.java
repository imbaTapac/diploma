package com.student.rating.utility;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.student.rating.entity.*;
import com.student.rating.repository.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас
 */

public final class StaticDataEngine {
    private static final Logger LOG = LoggerFactory.getLogger(StaticDataEngine.class);

    public static List<Group> GROUP_LIST;
    public static List<Faculty> FACULTY_LIST;
    public static List<OKR> OKR_LIST;
    public static List<Specialty> SPECIALTY_LIST;
    public static List<Block> BLOCK_LIST;
    public static List<Student> STUDENT_LIST;

    public static List<String> GROUP_NAME_LIST = new ArrayList<>();

    private final BlockRepository blockRepository;
    private final FacultyRepository facultyRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final OKRRepository okrRepository;

    private StaticDataEngine(BlockRepository blockRep,GroupRepository groupRep,FacultyRepository facultyRep,
                             StudentRepository studentRep,OKRRepository okrRep) {
        this.blockRepository = blockRep;
        this.facultyRepository = facultyRep;
        this.groupRepository = groupRep;
        this.studentRepository = studentRep;
        this.okrRepository = okrRep;

        int subblockCount = 0;
        int paragraphCount = 0;
        LOG.info("Loading static data");

        long start = DateTime.now().getMillis();
        BLOCK_LIST = blockRepository.findAll();
        GROUP_LIST = groupRepository.findAll();
        FACULTY_LIST = facultyRepository.findAll();
        STUDENT_LIST = studentRepository.findAll();
        OKR_LIST = okrRepository.findAll();
        long time = DateTime.now().getMillis() - start;

        for(Group group : GROUP_LIST){
            GROUP_NAME_LIST.add(group.getName());
        }

        LOG.info("Loaded {} blocks",BLOCK_LIST.size());
        for(Block block: BLOCK_LIST){
            subblockCount+= block.getSubblock().size();
            for(Subblock subblock : block.getSubblock()){
                paragraphCount = paragraphCount + subblock.getParagraph().size();
            }
        }
        LOG.info("Loaded {} subblocks",subblockCount);
        LOG.info("Loaded {} paragraphs",paragraphCount);
        LOG.info("Loaded {} groups",GROUP_LIST.size());
        LOG.info("Loaded {} faculties",FACULTY_LIST.size());
        LOG.info("Loaded {} students",STUDENT_LIST.size());
        LOG.info("Loaded {} okrs",OKR_LIST.size());

        long seconds = time/1000;
        String timeMsg = seconds > 0 ? seconds + " seconds" : time + " milliseconds";
        LOG.info("Static Data loaded in: {}",timeMsg);
    }

}
