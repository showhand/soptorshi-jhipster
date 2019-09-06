/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MonthlyBalanceDetailComponent } from 'app/entities/monthly-balance/monthly-balance-detail.component';
import { MonthlyBalance } from 'app/shared/model/monthly-balance.model';

describe('Component Tests', () => {
    describe('MonthlyBalance Management Detail Component', () => {
        let comp: MonthlyBalanceDetailComponent;
        let fixture: ComponentFixture<MonthlyBalanceDetailComponent>;
        const route = ({ data: of({ monthlyBalance: new MonthlyBalance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MonthlyBalanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MonthlyBalanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MonthlyBalanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.monthlyBalance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
