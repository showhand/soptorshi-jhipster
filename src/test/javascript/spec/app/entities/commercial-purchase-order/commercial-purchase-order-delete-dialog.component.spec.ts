/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPurchaseOrderDeleteDialogComponent } from 'app/entities/commercial-purchase-order/commercial-purchase-order-delete-dialog.component';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order/commercial-purchase-order.service';

describe('Component Tests', () => {
    describe('CommercialPurchaseOrder Management Delete Component', () => {
        let comp: CommercialPurchaseOrderDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialPurchaseOrderDeleteDialogComponent>;
        let service: CommercialPurchaseOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPurchaseOrderDeleteDialogComponent]
            })
                .overrideTemplate(CommercialPurchaseOrderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPurchaseOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPurchaseOrderService);
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
