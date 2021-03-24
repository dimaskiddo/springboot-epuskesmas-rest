package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    private QueueRepository queueRepository;

    @Autowired
    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }
}
