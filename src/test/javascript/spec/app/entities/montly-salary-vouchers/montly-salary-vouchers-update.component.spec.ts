/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MontlySalaryVouchersUpdateComponent } from 'app/entities/montly-salary-vouchers/montly-salary-vouchers-update.component';
import { MontlySalaryVouchersService } from 'app/entities/montly-salary-vouchers/montly-salary-vouchers.service';
import { MontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';

describe('Component Tests', () => {
    describe('MontlySalaryVouchers Management Update Component', () => {
        let comp: MontlySalaryVouchersUpdateComponent;
        let fixture: ComponentFixture<MontlySalaryVouchersUpdateComponent>;
        let service: MontlySalaryVouchersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MontlySalaryVouchersUpdateComponent]
            })
                .overrideTemplate(MontlySalaryVouchersUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MontlySalaryVouchersUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MontlySalaryVouchersService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MontlySalaryVouchers(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.montlySalaryVouchers = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MontlySalaryVouchers();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.montlySalaryVouchers = entity;
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
