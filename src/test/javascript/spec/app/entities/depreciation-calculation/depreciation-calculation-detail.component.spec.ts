/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DepreciationCalculationDetailComponent } from 'app/entities/depreciation-calculation/depreciation-calculation-detail.component';
import { DepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';

describe('Component Tests', () => {
    describe('DepreciationCalculation Management Detail Component', () => {
        let comp: DepreciationCalculationDetailComponent;
        let fixture: ComponentFixture<DepreciationCalculationDetailComponent>;
        const route = ({ data: of({ depreciationCalculation: new DepreciationCalculation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepreciationCalculationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepreciationCalculationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepreciationCalculationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.depreciationCalculation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
