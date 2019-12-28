/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseOrderMessagesDeleteDialogComponent } from 'app/entities/purchase-order-messages/purchase-order-messages-delete-dialog.component';
import { PurchaseOrderMessagesService } from 'app/entities/purchase-order-messages/purchase-order-messages.service';

describe('Component Tests', () => {
    describe('PurchaseOrderMessages Management Delete Component', () => {
        let comp: PurchaseOrderMessagesDeleteDialogComponent;
        let fixture: ComponentFixture<PurchaseOrderMessagesDeleteDialogComponent>;
        let service: PurchaseOrderMessagesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseOrderMessagesDeleteDialogComponent]
            })
                .overrideTemplate(PurchaseOrderMessagesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseOrderMessagesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseOrderMessagesService);
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
