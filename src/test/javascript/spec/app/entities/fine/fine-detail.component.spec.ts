/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FineDetailComponent } from 'app/entities/fine/fine-detail.component';
import { Fine } from 'app/shared/model/fine.model';

describe('Component Tests', () => {
    describe('Fine Management Detail Component', () => {
        let comp: FineDetailComponent;
        let fixture: ComponentFixture<FineDetailComponent>;
        const route = ({ data: of({ fine: new Fine(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
