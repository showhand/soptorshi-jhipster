/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FinancialAccountYearUpdateComponent } from 'app/entities/financial-account-year/financial-account-year-update.component';
import { FinancialAccountYearService } from 'app/entities/financial-account-year/financial-account-year.service';
import { FinancialAccountYear } from 'app/shared/model/financial-account-year.model';

describe('Component Tests', () => {
    describe('FinancialAccountYear Management Update Component', () => {
        let comp: FinancialAccountYearUpdateComponent;
        let fixture: ComponentFixture<FinancialAccountYearUpdateComponent>;
        let service: FinancialAccountYearService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FinancialAccountYearUpdateComponent]
            })
                .overrideTemplate(FinancialAccountYearUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FinancialAccountYearUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialAccountYearService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FinancialAccountYear(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.financialAccountYear = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FinancialAccountYear();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.financialAccountYear = entity;
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
