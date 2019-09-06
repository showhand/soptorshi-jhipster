/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MonthlyBalanceUpdateComponent } from 'app/entities/monthly-balance/monthly-balance-update.component';
import { MonthlyBalanceService } from 'app/entities/monthly-balance/monthly-balance.service';
import { MonthlyBalance } from 'app/shared/model/monthly-balance.model';

describe('Component Tests', () => {
    describe('MonthlyBalance Management Update Component', () => {
        let comp: MonthlyBalanceUpdateComponent;
        let fixture: ComponentFixture<MonthlyBalanceUpdateComponent>;
        let service: MonthlyBalanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MonthlyBalanceUpdateComponent]
            })
                .overrideTemplate(MonthlyBalanceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MonthlyBalanceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlyBalanceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MonthlyBalance(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.monthlyBalance = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MonthlyBalance();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.monthlyBalance = entity;
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
