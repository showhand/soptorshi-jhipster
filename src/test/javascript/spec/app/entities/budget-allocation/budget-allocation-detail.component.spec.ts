/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { BudgetAllocationDetailComponent } from 'app/entities/budget-allocation/budget-allocation-detail.component';
import { BudgetAllocation } from 'app/shared/model/budget-allocation.model';

describe('Component Tests', () => {
    describe('BudgetAllocation Management Detail Component', () => {
        let comp: BudgetAllocationDetailComponent;
        let fixture: ComponentFixture<BudgetAllocationDetailComponent>;
        const route = ({ data: of({ budgetAllocation: new BudgetAllocation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [BudgetAllocationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BudgetAllocationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BudgetAllocationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.budgetAllocation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
