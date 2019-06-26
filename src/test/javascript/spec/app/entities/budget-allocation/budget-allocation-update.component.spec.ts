/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { BudgetAllocationUpdateComponent } from 'app/entities/budget-allocation/budget-allocation-update.component';
import { BudgetAllocationService } from 'app/entities/budget-allocation/budget-allocation.service';
import { BudgetAllocation } from 'app/shared/model/budget-allocation.model';

describe('Component Tests', () => {
    describe('BudgetAllocation Management Update Component', () => {
        let comp: BudgetAllocationUpdateComponent;
        let fixture: ComponentFixture<BudgetAllocationUpdateComponent>;
        let service: BudgetAllocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [BudgetAllocationUpdateComponent]
            })
                .overrideTemplate(BudgetAllocationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BudgetAllocationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetAllocationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BudgetAllocation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.budgetAllocation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BudgetAllocation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.budgetAllocation = entity;
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
