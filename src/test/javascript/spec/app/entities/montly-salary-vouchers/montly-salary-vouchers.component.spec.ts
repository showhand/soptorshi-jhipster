/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { MontlySalaryVouchersComponent } from 'app/entities/montly-salary-vouchers/montly-salary-vouchers.component';
import { MontlySalaryVouchersService } from 'app/entities/montly-salary-vouchers/montly-salary-vouchers.service';
import { MontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';

describe('Component Tests', () => {
    describe('MontlySalaryVouchers Management Component', () => {
        let comp: MontlySalaryVouchersComponent;
        let fixture: ComponentFixture<MontlySalaryVouchersComponent>;
        let service: MontlySalaryVouchersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MontlySalaryVouchersComponent],
                providers: []
            })
                .overrideTemplate(MontlySalaryVouchersComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MontlySalaryVouchersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MontlySalaryVouchersService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MontlySalaryVouchers(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.montlySalaryVouchers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
