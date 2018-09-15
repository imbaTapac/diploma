package student_rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student_rating.entity.Block;
import student_rating.repository.BlockRepository;

/**
 * Created by Тарас on 31.05.2018.
 */
@Service
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;

    @Autowired
    public BlockServiceImpl(BlockRepository blockRepository){
        this.blockRepository = blockRepository;
    }

    @Override
    public Block save(Block block) {
        return blockRepository.save(block);
    }
}
