/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FinancialAccountYearDetailComponent } from 'app/entities/financial-account-year/financial-account-year-detail.component';
import { FinancialAccountYear } from 'app/shared/model/financial-account-year.model';

describe('Component Tests', () => {
    describe('FinancialAccountYear Management Detail Component', () => {
        let comp: FinancialAccountYearDetailComponent;
        let fixture: ComponentFixture<FinancialAccountYearDetailComponent>;
        const route = ({ data: of({ financialAccountYear: new FinancialAccountYear(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FinancialAccountYearDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FinancialAccountYearDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FinancialAccountYearDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.financialAccountYear).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
