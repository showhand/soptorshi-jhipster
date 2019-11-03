/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPurchaseOrderItemDeleteDialogComponent } from 'app/entities/commercial-purchase-order-item/commercial-purchase-order-item-delete-dialog.component';
import { CommercialPurchaseOrderItemService } from 'app/entities/commercial-purchase-order-item/commercial-purchase-order-item.service';

describe('Component Tests', () => {
    describe('CommercialPurchaseOrderItem Management Delete Component', () => {
        let comp: CommercialPurchaseOrderItemDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialPurchaseOrderItemDeleteDialogComponent>;
        let service: CommercialPurchaseOrderItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPurchaseOrderItemDeleteDialogComponent]
            })
                .overrideTemplate(CommercialPurchaseOrderItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPurchaseOrderItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPurchaseOrderItemService);
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
