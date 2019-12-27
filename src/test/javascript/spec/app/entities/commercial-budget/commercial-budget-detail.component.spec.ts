/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialBudgetDetailComponent } from 'app/entities/commercial-budget/commercial-budget-detail.component';
import { CommercialBudget } from 'app/shared/model/commercial-budget.model';

describe('Component Tests', () => {
    describe('CommercialBudget Management Detail Component', () => {
        let comp: CommercialBudgetDetailComponent;
        let fixture: ComponentFixture<CommercialBudgetDetailComponent>;
        const route = ({ data: of({ commercialBudget: new CommercialBudget(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialBudgetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialBudgetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialBudgetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialBudget).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
