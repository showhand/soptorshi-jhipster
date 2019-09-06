/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DtTransactionDetailComponent } from 'app/entities/dt-transaction/dt-transaction-detail.component';
import { DtTransaction } from 'app/shared/model/dt-transaction.model';

describe('Component Tests', () => {
    describe('DtTransaction Management Detail Component', () => {
        let comp: DtTransactionDetailComponent;
        let fixture: ComponentFixture<DtTransactionDetailComponent>;
        const route = ({ data: of({ dtTransaction: new DtTransaction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DtTransactionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DtTransactionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DtTransactionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dtTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
