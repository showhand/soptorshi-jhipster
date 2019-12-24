/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialBudgetUpdateComponent } from 'app/entities/commercial-budget/commercial-budget-update.component';
import { CommercialBudgetService } from 'app/entities/commercial-budget/commercial-budget.service';
import { CommercialBudget } from 'app/shared/model/commercial-budget.model';

describe('Component Tests', () => {
    describe('CommercialBudget Management Update Component', () => {
        let comp: CommercialBudgetUpdateComponent;
        let fixture: ComponentFixture<CommercialBudgetUpdateComponent>;
        let service: CommercialBudgetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialBudgetUpdateComponent]
            })
                .overrideTemplate(CommercialBudgetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialBudgetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialBudgetService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialBudget(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialBudget = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialBudget();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialBudget = entity;
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
