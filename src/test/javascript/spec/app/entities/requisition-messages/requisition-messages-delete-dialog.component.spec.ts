/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionMessagesDeleteDialogComponent } from 'app/entities/requisition-messages/requisition-messages-delete-dialog.component';
import { RequisitionMessagesService } from 'app/entities/requisition-messages/requisition-messages.service';

describe('Component Tests', () => {
    describe('RequisitionMessages Management Delete Component', () => {
        let comp: RequisitionMessagesDeleteDialogComponent;
        let fixture: ComponentFixture<RequisitionMessagesDeleteDialogComponent>;
        let service: RequisitionMessagesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionMessagesDeleteDialogComponent]
            })
                .overrideTemplate(RequisitionMessagesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequisitionMessagesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequisitionMessagesService);
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
