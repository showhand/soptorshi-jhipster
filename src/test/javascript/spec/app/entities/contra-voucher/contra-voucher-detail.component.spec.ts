/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ContraVoucherDetailComponent } from 'app/entities/contra-voucher/contra-voucher-detail.component';
import { ContraVoucher } from 'app/shared/model/contra-voucher.model';

describe('Component Tests', () => {
    describe('ContraVoucher Management Detail Component', () => {
        let comp: ContraVoucherDetailComponent;
        let fixture: ComponentFixture<ContraVoucherDetailComponent>;
        const route = ({ data: of({ contraVoucher: new ContraVoucher(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ContraVoucherDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContraVoucherDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContraVoucherDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contraVoucher).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
