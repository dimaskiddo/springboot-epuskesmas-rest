package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.model.QueueModel;
import co.id.telkom.epuskesmas.repository.QueueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueService {

    @Autowired
    private ModelMapper modelMapper;

    private QueueRepository queueRepository;

    @Autowired
    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public QueueModel createQueue(QueueModel queueModel) {
        return queueRepository.save(queueModel);
    }

    public Iterable<QueueModel> getQueue() {
        return queueRepository.findAll();
    }

    public Optional<QueueModel> getQueueById(int id) {
        return queueRepository.findById(id);
    }

    public Iterable<QueueModel> getQueueByIdUser(int id) {
        return queueRepository.getQueueByIdUser(id);
    }

    public Iterable<QueueModel> getQueueByIdPoli(int id) {
        return queueRepository.getQueueByIdPoli(id);
    }

    public Iterable<QueueModel> getQueueByIdDokter(int id) {
        return queueRepository.getQueueByIdDokter(id);
    }

    public QueueModel updateQueue(int id, QueueModel queueModel) {
        Optional<QueueModel> currentQueue = queueRepository.findById(id);

        if (currentQueue.isPresent()) {
            return queueRepository.save(queueModel);
        }

        return null;
    }

    public QueueModel patchQueue(int id, QueueModel queueModel) {
        Optional<QueueModel> currentQueue = queueRepository.findById(id);

        if (currentQueue.isPresent()) {
            QueueModel dataQueue = currentQueue.get();

            if (queueModel.getIdUser() != 0) {
                dataQueue.setIdUser(queueModel.getIdUser());
            }

            if (queueModel.getIdPoli() != 0) {
                dataQueue.setIdPoli(queueModel.getIdPoli());
            }

            if (queueModel.getIdDokter() != 0) {
                dataQueue.setIdDokter(queueModel.getIdDokter());
            }

            if (queueModel.getNoAntrian() != 0) {
                dataQueue.setNoAntrian(queueModel.getNoAntrian());
            }

            if (queueModel.getWaktuAntrian() != null) {
                dataQueue.setWaktuAntrian(queueModel.getWaktuAntrian());
            }

            return queueRepository.save(dataQueue);
        }

        return null;
    }

    public boolean deleteQueueById(int id) {
        Optional<QueueModel> currentQueue = queueRepository.findById(id);

        if (currentQueue.isPresent()) {
            queueRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
