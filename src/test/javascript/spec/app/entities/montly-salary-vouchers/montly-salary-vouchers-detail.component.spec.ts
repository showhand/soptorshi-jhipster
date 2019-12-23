/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MontlySalaryVouchersDetailComponent } from 'app/entities/montly-salary-vouchers/montly-salary-vouchers-detail.component';
import { MontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';

describe('Component Tests', () => {
    describe('MontlySalaryVouchers Management Detail Component', () => {
        let comp: MontlySalaryVouchersDetailComponent;
        let fixture: ComponentFixture<MontlySalaryVouchersDetailComponent>;
        const route = ({ data: of({ montlySalaryVouchers: new MontlySalaryVouchers(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MontlySalaryVouchersDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MontlySalaryVouchersDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MontlySalaryVouchersDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.montlySalaryVouchers).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
