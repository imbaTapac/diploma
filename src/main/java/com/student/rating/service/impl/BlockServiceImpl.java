package com.student.rating.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.student.rating.entity.Block;
import com.student.rating.repository.BlockRepository;
import com.student.rating.service.BlockService;

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
