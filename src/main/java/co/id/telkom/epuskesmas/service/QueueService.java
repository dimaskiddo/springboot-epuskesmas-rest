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

    public Iterable<QueueModel> getAllQueue() {
        return queueRepository.findAll();
    }

    public Optional<QueueModel> getQueueById(int id) {
        return queueRepository.findById(id);
    }

    public Iterable<QueueModel> getQueueByIdUser(int id) {
        return queueRepository.findByIdUser(id);
    }

    public Iterable<QueueModel> getQueueByIdPoli(int id) {
        return queueRepository.findByIdPoli(id);
    }

    public Iterable<QueueModel> getQueueByIdDokter(int id) {
        return queueRepository.findByIdDokter(id);
    }

    public Iterable<QueueModel> getQueueByNoAntrian(int noAntrian) {
        return queueRepository.findByNoAntrian(noAntrian);
    }

    public QueueModel updateQueueById(int id, QueueModel queueModel) {
        Optional<QueueModel> currentQueue = queueRepository.findById(id);

        if (currentQueue.isPresent()) {
            queueModel.setId(id);
            return queueRepository.save(queueModel);
        }

        return null;
    }

    public QueueModel patchQueueById(int id, QueueModel queueModel) {
        Optional<QueueModel> currentQueue = queueRepository.findById(id);

        if (currentQueue.isPresent()) {
            QueueModel dataQueue = currentQueue.get();

            if (queueModel.getIdUser() > 0) {
                dataQueue.setIdUser(queueModel.getIdUser());
            }

            if (queueModel.getIdPoli() > 0) {
                dataQueue.setIdPoli(queueModel.getIdPoli());
            }

            if (queueModel.getIdDokter() > 0) {
                dataQueue.setIdDokter(queueModel.getIdDokter());
            }

            if (queueModel.getNoAntrian() > 0) {
                dataQueue.setNoAntrian(queueModel.getNoAntrian());
            }

            if (queueModel.getWaktuAntrian() != null && !queueModel.getWaktuAntrian().isEmpty()) {
                dataQueue.setWaktuAntrian(queueModel.getWaktuAntrian());
            }

            dataQueue.setId(id);
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
