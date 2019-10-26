/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ReceiptVoucherUpdateComponent } from 'app/entities/receipt-voucher/receipt-voucher-update.component';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher/receipt-voucher.service';
import { ReceiptVoucher } from 'app/shared/model/receipt-voucher.model';

describe('Component Tests', () => {
    describe('ReceiptVoucher Management Update Component', () => {
        let comp: ReceiptVoucherUpdateComponent;
        let fixture: ComponentFixture<ReceiptVoucherUpdateComponent>;
        let service: ReceiptVoucherService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ReceiptVoucherUpdateComponent]
            })
                .overrideTemplate(ReceiptVoucherUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReceiptVoucherUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceiptVoucherService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReceiptVoucher(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.receiptVoucher = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReceiptVoucher();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.receiptVoucher = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
