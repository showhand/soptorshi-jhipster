/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ReceiptVoucherDetailComponent } from 'app/entities/receipt-voucher/receipt-voucher-detail.component';
import { ReceiptVoucher } from 'app/shared/model/receipt-voucher.model';

describe('Component Tests', () => {
    describe('ReceiptVoucher Management Detail Component', () => {
        let comp: ReceiptVoucherDetailComponent;
        let fixture: ComponentFixture<ReceiptVoucherDetailComponent>;
        const route = ({ data: of({ receiptVoucher: new ReceiptVoucher(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ReceiptVoucherDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReceiptVoucherDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReceiptVoucherDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.receiptVoucher).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
