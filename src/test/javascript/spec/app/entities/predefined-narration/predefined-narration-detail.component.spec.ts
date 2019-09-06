/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PredefinedNarrationDetailComponent } from 'app/entities/predefined-narration/predefined-narration-detail.component';
import { PredefinedNarration } from 'app/shared/model/predefined-narration.model';

describe('Component Tests', () => {
    describe('PredefinedNarration Management Detail Component', () => {
        let comp: PredefinedNarrationDetailComponent;
        let fixture: ComponentFixture<PredefinedNarrationDetailComponent>;
        const route = ({ data: of({ predefinedNarration: new PredefinedNarration(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PredefinedNarrationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PredefinedNarrationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PredefinedNarrationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.predefinedNarration).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
