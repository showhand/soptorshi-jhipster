/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { StockStatusDetailComponent } from 'app/entities/stock-status/stock-status-detail.component';
import { StockStatus } from 'app/shared/model/stock-status.model';

describe('Component Tests', () => {
    describe('StockStatus Management Detail Component', () => {
        let comp: StockStatusDetailComponent;
        let fixture: ComponentFixture<StockStatusDetailComponent>;
        const route = ({ data: of({ stockStatus: new StockStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stockStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
