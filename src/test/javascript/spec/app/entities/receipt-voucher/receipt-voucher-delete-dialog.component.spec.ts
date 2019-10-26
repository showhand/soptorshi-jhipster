/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { ReceiptVoucherDeleteDialogComponent } from 'app/entities/receipt-voucher/receipt-voucher-delete-dialog.component';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher/receipt-voucher.service';

describe('Component Tests', () => {
    describe('ReceiptVoucher Management Delete Component', () => {
        let comp: ReceiptVoucherDeleteDialogComponent;
        let fixture: ComponentFixture<ReceiptVoucherDeleteDialogComponent>;
        let service: ReceiptVoucherService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ReceiptVoucherDeleteDialogComponent]
            })
                .overrideTemplate(ReceiptVoucherDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReceiptVoucherDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceiptVoucherService);
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
