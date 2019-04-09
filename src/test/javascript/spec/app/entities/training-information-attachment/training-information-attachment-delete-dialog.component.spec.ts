/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { TrainingInformationAttachmentDeleteDialogComponent } from 'app/entities/training-information-attachment/training-information-attachment-delete-dialog.component';
import { TrainingInformationAttachmentService } from 'app/entities/training-information-attachment/training-information-attachment.service';

describe('Component Tests', () => {
    describe('TrainingInformationAttachment Management Delete Component', () => {
        let comp: TrainingInformationAttachmentDeleteDialogComponent;
        let fixture: ComponentFixture<TrainingInformationAttachmentDeleteDialogComponent>;
        let service: TrainingInformationAttachmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TrainingInformationAttachmentDeleteDialogComponent]
            })
                .overrideTemplate(TrainingInformationAttachmentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrainingInformationAttachmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingInformationAttachmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
