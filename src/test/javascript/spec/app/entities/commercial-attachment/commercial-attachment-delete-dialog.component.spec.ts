/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialAttachmentDeleteDialogComponent } from 'app/entities/commercial-attachment/commercial-attachment-delete-dialog.component';
import { CommercialAttachmentService } from 'app/entities/commercial-attachment/commercial-attachment.service';

describe('Component Tests', () => {
    describe('CommercialAttachment Management Delete Component', () => {
        let comp: CommercialAttachmentDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialAttachmentDeleteDialogComponent>;
        let service: CommercialAttachmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialAttachmentDeleteDialogComponent]
            })
                .overrideTemplate(CommercialAttachmentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialAttachmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialAttachmentService);
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
