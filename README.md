# abbres

[![Build Status](https://travis-ci.org/michelole/abbres.svg?branch=master)](https://travis-ci.org/michelole/abbres)
[![Coverage Status](https://coveralls.io/repos/github/michelole/abbres/badge.svg?branch=master)](https://coveralls.io/github/michelole/abbres?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

An abbreviation expansion module based on n-gram matching developed at the [Institute for Medical Informatics, Statistics and Documentation at the Medical University of Graz (Austria)](https://www.medunigraz.at/imi/en/).

Note that a more recent version of this work using word embeddings for acronyms expansion is available at https://github.com/bst-mug/acres.

## Citing

If you use data or code in your work, please cite our [MEDINFO 2017 paper](http://ebooks.iospress.nl/publication/48206):

```
@inproceedings{oleynik2017unsupervised,
  author    = {Michel Oleynik and
               Markus Kreuzthaler and
               Stefan Schulz},
  editor    = {Adi V. Gundlapalli and
               Marie{-}Christine Jaulent and
               Dongsheng Zhao},
  title     = {Unsupervised Abbreviation Expansion in Clinical Narratives},
  booktitle = {{MEDINFO} 2017: Precision Healthcare through Informatics - Proceedings
               of the 16th World Congress on Medical and Health Informatics, Hangzhou,
               China, 21-25 August 2017},
  series    = {Studies in Health Technology and Informatics},
  volume    = {245},
  pages     = {539--543},
  publisher = {{IOS} Press},
  year      = {2017},
  url       = {https://doi.org/10.3233/978-1-61499-830-3-539},
  doi       = {10.3233/978-1-61499-830-3-539}
}
```

## Dependencies
- Tab-separated (TSV) bigram list generated from the corpus
- Tab-separated (TSV) unigram list generated from the corpus
