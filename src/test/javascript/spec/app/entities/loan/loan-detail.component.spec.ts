/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { LoanDetailComponent } from 'app/entities/loan/loan-detail.component';
import { Loan } from 'app/shared/model/loan.model';

describe('Component Tests', () => {
    describe('Loan Management Detail Component', () => {
        let comp: LoanDetailComponent;
        let fixture: ComponentFixture<LoanDetailComponent>;
        const route = ({ data: of({ loan: new Loan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LoanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LoanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.loan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
