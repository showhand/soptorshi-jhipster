/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { StockInProcessDetailComponent } from 'app/entities/stock-in-process/stock-in-process-detail.component';
import { StockInProcess } from 'app/shared/model/stock-in-process.model';

describe('Component Tests', () => {
    describe('StockInProcess Management Detail Component', () => {
        let comp: StockInProcessDetailComponent;
        let fixture: ComponentFixture<StockInProcessDetailComponent>;
        const route = ({ data: of({ stockInProcess: new StockInProcess(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockInProcessDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockInProcessDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockInProcessDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stockInProcess).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
